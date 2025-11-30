package com.aicloudflare.musicbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aicloudflare.musicbox.databinding.FragmentPlayerBinding

class PlayerFragment : Fragment() {

    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!

    // Tránh scroll auto bị hiểu nhầm là người dùng scroll
    private var isAutoScrolling = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ====================== MỞ / ĐÓNG LYRICS ======================
        binding.btnExpandLyrics.setOnClickListener {

            val lyricRoot = binding.includeLyrics.root
            val expandLayout = binding.layoutExpandLyrics
            val divider = binding.lineLyricsDivider

            val willShow = lyricRoot.visibility == View.GONE

            // Hiện / ẩn lyrics
            lyricRoot.visibility = if (willShow) View.VISIBLE else View.GONE

            // Ẩn / hiện mũi tên + chữ Lyrics
            expandLayout.visibility = if (willShow) View.GONE else View.VISIBLE

            // Ẩn / hiện line
            divider.visibility = if (willShow) View.VISIBLE else View.GONE

            // Auto scroll khi mở lyrics
            if (willShow) {
                isAutoScrolling = true

                binding.root.post {
                    val offsetDp = 140
                    val offsetPx = (offsetDp * resources.displayMetrics.density).toInt()

                    binding.root.smoothScrollTo(
                        0,
                        lyricRoot.top - offsetPx
                    )

                    binding.root.postDelayed({
                        isAutoScrolling = false
                    }, 300)
                }
            }
        }

        // ====================== ĐÓNG LYRICS KHI CUỘN XUỐNG ======================
        var lastScrollY = 0

        binding.scrollView.viewTreeObserver.addOnScrollChangedListener {

            if (isAutoScrolling) return@addOnScrollChangedListener

            val scrollY = binding.scrollView.scrollY

            // Xác định hướng cuộn
            val isScrollingDown = scrollY > lastScrollY

            // Chỉ đóng lyrics KHI:
            // 1. Người dùng đang cuộn xuống
            // 2. Scroll vượt ngưỡng 200px
            // 3. Lyrics đang mở
            if (isScrollingDown &&
                scrollY > 200 &&
                binding.includeLyrics.root.visibility == View.VISIBLE
            ) {
                binding.includeLyrics.root.visibility = View.GONE
                binding.layoutExpandLyrics.visibility = View.VISIBLE
                binding.lineLyricsDivider.visibility = View.GONE
            }

            lastScrollY = scrollY
        }

        // ====================== NÚT BACK ======================
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
